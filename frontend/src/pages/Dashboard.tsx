import React, { useEffect, useState } from "react";
import axios from "axios";
import {
  AppBar,
  Toolbar,
  Typography,
  Tabs,
  Tab,
  Box,
  Card,
  Grid,
  TextField,
  Switch,
  FormControlLabel,
  Button,
} from "@mui/material";
import { Line, Bar } from "react-chartjs-2";
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  BarElement,
  PointElement,
  LineElement,
  Title,
  Tooltip,
  Legend,
} from "chart.js";
import ArrowUpwardIcon from "@mui/icons-material/ArrowUpward";
import ArrowDownwardIcon from "@mui/icons-material/ArrowDownward";
import SettingsIcon from "@mui/icons-material/Settings";

ChartJS.register(
  CategoryScale,
  LinearScale,
  BarElement,
  PointElement,
  LineElement,
  Title,
  Tooltip,
  Legend
);

interface PortfolioItem {
  label: string;
  currentPrice: number;      // Current price from backend
  predictedValue: number;    // Predicted value
  predictedReturn: number;   // Predicted return %
}

interface Profile {
  id: number;
  name: string;
  email: string;
}

interface Notifications {
  priceAlerts: boolean;
  newsletter: boolean;
}

const Dashboard: React.FC = () => {
  const [tabValue, setTabValue] = useState(0);
  const [portfolio, setPortfolio] = useState<PortfolioItem[]>([]);
  const [totalCurrentValue, setTotalCurrentValue] = useState<number>(0);
  const [totalPredictedValue, setTotalPredictedValue] = useState<number>(0);
  const [profile, setProfile] = useState<Profile>({ id: 1, name: "", email: "" });
  const [notifications, setNotifications] = useState<Notifications>({
    priceAlerts: true,
    newsletter: false,
  });
  const [loading, setLoading] = useState(true);

  const handleTabChange = (_: React.SyntheticEvent, newValue: number) => setTabValue(newValue);

  // Fetch Quantum AI predictions
  const fetchPortfolio = async () => {
    try {
      const res = await axios.get("http://localhost:8080/api/quantum-ai/predict");
      const data = res.data;

      if (data?.predictions && Array.isArray(data.predictions)) {
        setPortfolio(data.predictions);
      }

      setTotalCurrentValue(data?.totalCurrentValue ?? 0);
      setTotalPredictedValue(data?.totalPredictedValue ?? 0);
    } catch (error) {
      console.error("Error fetching predictions:", error);
    }
  };

  // Fetch user profile and notifications
  const fetchUserData = async () => {
    try {
      const res = await axios.get("http://localhost:8080/api/users/1");
      const data = res.data;

      setProfile({ id: data.id, name: data.name, email: data.email });
      setNotifications({
        priceAlerts: data.priceAlerts ?? true,
        newsletter: data.newsletter ?? false,
      });
      setLoading(false);
    } catch (error) {
      console.error("Error fetching user data:", error);
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchPortfolio();
    fetchUserData();
    const interval = setInterval(fetchPortfolio, 30000); // refresh every 30s
    return () => clearInterval(interval);
  }, []);

  const saveProfile = async () => {
    try {
      await axios.post("http://localhost:8080/api/users/updateProfile", profile);
      alert("Profile saved successfully!");
    } catch (err) {
      console.error("Error saving profile:", err);
      alert("Failed to save profile.");
    }
  };

  const saveNotifications = async () => {
    try {
      await axios.post("http://localhost:8080/api/users/updateNotifications", notifications);
      alert("Notifications saved successfully!");
    } catch (err) {
      console.error("Error saving notifications:", err);
      alert("Failed to save notifications.");
    }
  };

  return (
    <Box sx={{ backgroundColor: "#f5f7fa", minHeight: "100vh" }}>
      {/* Navbar */}
      <AppBar position="static" sx={{ backgroundColor: "#0d1b2a" }}>
        <Toolbar>
          <Typography variant="h6" sx={{ flexGrow: 1 }}>
            QuantumInvest Dashboard
          </Typography>
          <Tabs value={tabValue} onChange={handleTabChange} textColor="inherit" indicatorColor="secondary">
            <Tab label="Dashboard" />
            <Tab label="Activity" />
            <Tab label="Settings" />
          </Tabs>
        </Toolbar>
      </AppBar>

      <Box sx={{ p: 3 }}>
        {/* Dashboard Tab */}
        {tabValue === 0 && (
          <Box>
            {/* Portfolio Summary */}
            <Card sx={{ mb: 3, p: 3, borderRadius: 3, background: "linear-gradient(90deg, #0d1b2a, #1e3c72)", color: "white", boxShadow: 4 }}>
              <Typography variant="h5">Portfolio Summary</Typography>
              <Typography>Total Current Value: ${totalCurrentValue?.toFixed(2) ?? "0.00"}</Typography>
              <Typography>Total Predicted Value: ${totalPredictedValue?.toFixed(2) ?? "0.00"}</Typography>
            </Card>

            {/* Portfolio Bar Chart */}
            <Card sx={{ mb: 3, p: 2, borderRadius: 2, boxShadow: 3 }}>
              <Typography variant="h6" gutterBottom>Quantum AI Predicted Portfolio</Typography>
              <Bar
                data={{
                  labels: portfolio.map((s) => s.label),
                  datasets: [
                    {
                      label: "Current Value ($)",
                      data: portfolio.map((s) => s.currentPrice ?? 0),
                      backgroundColor: "rgba(75, 192, 192, 0.6)",
                    },
                    {
                      label: "Predicted Value ($)",
                      data: portfolio.map((s) => s.predictedValue ?? 0),
                      backgroundColor: "rgba(54, 162, 235, 0.6)",
                    },
                  ],
                }}
                options={{ responsive: true, plugins: { legend: { position: "top" } } }}
              />
            </Card>

            {/* Stock Cards */}
            <Grid container spacing={3}>
              {portfolio.map((stock, i) => (
                <Grid item xs={12} sm={6} md={4} key={i}>
                  <Card sx={{ p: 2, borderRadius: 2, boxShadow: 3, transition: "0.3s", "&:hover": { transform: "scale(1.05)", boxShadow: 6 } }}>
                    <Typography variant="h6">{stock.label}</Typography>
                    <Typography>Current Value: ${stock.currentPrice ?? 0}</Typography>
                    <Typography>Predicted Value: ${stock.predictedValue ?? 0}</Typography>
                    <Typography>
                      Predicted Return: <b style={{ color: stock.predictedReturn >= 0 ? "green" : "red" }}>{stock.predictedReturn ?? 0}%</b>
                    </Typography>
                    <Box sx={{ mt: 1 }}>
                      {stock.predictedReturn >= 0 ? <ArrowUpwardIcon color="success" /> : <ArrowDownwardIcon color="error" />}
                    </Box>
                    <Line
                      data={{
                        labels: ["Current", "Predicted"],
                        datasets: [
                          {
                            label: stock.label,
                            data: [stock.currentPrice ?? 0, stock.predictedValue ?? 0],
                            borderColor: stock.predictedReturn >= 0 ? "green" : "red",
                            backgroundColor: "rgba(0,0,0,0)",
                          },
                        ],
                      }}
                      options={{ plugins: { legend: { display: false } } }}
                    />
                  </Card>
                </Grid>
              ))}
            </Grid>
          </Box>
        )}

        {/* Activity Tab */}
        {tabValue === 1 && (
          <Box>
            <Typography variant="h5" gutterBottom>Activity Feed</Typography>
            <Box sx={{ maxHeight: 400, overflowY: "auto" }}>
              {portfolio.map((s, i) => (
                <Card key={i} sx={{ mb: 2, p: 1.5, borderLeft: `4px solid ${s.predictedReturn >= 0 ? "green" : "red"}`, boxShadow: 1 }}>
                  <Typography variant="subtitle1">{s.label} updated prediction</Typography>
                  <Typography variant="body2">Current Value: ${s.currentPrice ?? 0}</Typography>
                  <Typography variant="body2">Predicted Value: ${s.predictedValue ?? 0}</Typography>
                  <Typography variant="body2">Predicted Return: {s.predictedReturn ?? 0}%</Typography>
                </Card>
              ))}
            </Box>
          </Box>
        )}

        {/* Settings Tab */}
        {tabValue === 2 && (
          <Box>
            <Typography variant="h5" gutterBottom>Settings</Typography>
            {loading ? <Typography>Loading settings...</Typography> : (
              <Grid container spacing={3}>
                {/* Profile */}
                <Grid item xs={12} md={6}>
                  <Card sx={{ p: 3, boxShadow: 3 }}>
                    <Typography variant="h6" gutterBottom><SettingsIcon sx={{ mr: 1 }} />Profile Settings</Typography>
                    <TextField fullWidth label="Name" value={profile.name} onChange={(e) => setProfile({ ...profile, name: e.target.value })} sx={{ mb: 2 }} />
                    <TextField fullWidth label="Email" value={profile.email} onChange={(e) => setProfile({ ...profile, email: e.target.value })} sx={{ mb: 2 }} />
                    <Button variant="contained" color="primary" onClick={saveProfile}>Save Profile</Button>
                  </Card>
                </Grid>

                {/* Notifications */}
                <Grid item xs={12} md={6}>
                  <Card sx={{ p: 3, boxShadow: 3 }}>
                    <Typography variant="h6" gutterBottom><SettingsIcon sx={{ mr: 1 }} />Notification Settings</Typography>
                    <FormControlLabel
                      control={<Switch checked={notifications.priceAlerts} onChange={(e) => setNotifications({ ...notifications, priceAlerts: e.target.checked })} color="primary" />}
                      label="Price Alerts"
                    />
                    <FormControlLabel
                      control={<Switch checked={notifications.newsletter} onChange={(e) => setNotifications({ ...notifications, newsletter: e.target.checked })} color="primary" />}
                      label="Newsletter"
                    />
                    <Button variant="contained" color="secondary" onClick={saveNotifications} sx={{ mt: 2 }}>Save Notifications</Button>
                  </Card>
                </Grid>
              </Grid>
            )}
          </Box>
        )}
      </Box>
    </Box>
  );
};

export default Dashboard;
