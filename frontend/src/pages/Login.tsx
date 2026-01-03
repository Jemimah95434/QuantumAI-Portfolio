import React, { useState } from "react";
import { Box, TextField, Button, Typography, Paper } from "@mui/material";
import client from "../api/client";

const Login: React.FC = () => {
  const [email, setEmail] = useState(""); // changed from username to email
  const [password, setPassword] = useState("");

  const handleLogin = async () => {
    try {
      // Send email and password to match your backend AuthController
      const res = await client.post("/api/auth/login", { email, password });
      
      // Optional: save a simple token or flag
      localStorage.setItem("token", "loggedin"); // backend currently returns just a message
      alert("Login successful");
      window.location.href = "/";
    } catch (err) {
      console.error(err);
      alert("Login failed");
    }
  };

  return (
    <Box maxWidth={400} mx="auto" mt={10} p={4} component={Paper}>
      <Typography variant="h5" mb={3}>
        Login
      </Typography>

      <TextField
        label="Email"
        fullWidth
        margin="normal"
        value={email}
        onChange={(e) => setEmail(e.target.value)}
      />

      <TextField
        label="Password"
        type="password"
        fullWidth
        margin="normal"
        value={password}
        onChange={(e) => setPassword(e.target.value)}
      />

      <Button
        variant="contained"
        color="primary"
        fullWidth
        sx={{ mt: 2 }}
        onClick={handleLogin}
      >
        Login
      </Button>
    </Box>
  );
};

export default Login;
