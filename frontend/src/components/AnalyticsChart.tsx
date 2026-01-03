import React from "react";
import { BarChart, Bar, XAxis, YAxis, Tooltip, ResponsiveContainer } from "recharts";

export interface AnalyticsItem {
  label: string;
  predictedReturn: number;
}

interface Props {
  analytics: AnalyticsItem[];
}

const AnalyticsChart: React.FC<Props> = ({ analytics }) => (
  <ResponsiveContainer width="100%" height={300}>
    <BarChart data={analytics} margin={{ top: 20, right: 30, left: 0, bottom: 0 }}>
      <XAxis dataKey="label" />
      <YAxis />
      <Tooltip />
      <Bar
        dataKey="predictedReturn"
        fill="#4f46e5"
        label={{ position: "top" }}
      />
    </BarChart>
  </ResponsiveContainer>
);

export default AnalyticsChart;
