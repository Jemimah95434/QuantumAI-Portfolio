import React from "react";
import {
  Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper
} from "@mui/material";

export interface PortfolioItem {
  id: number;
  name: string;
  amount: number;
  currentValue: number;
}

interface Props {
  portfolio: PortfolioItem[];
}

const PortfolioTable: React.FC<Props> = ({ portfolio }) => (
  <TableContainer component={Paper}>
    <Table>
      <TableHead>
        <TableRow>
          <TableCell>ID</TableCell>
          <TableCell>Name</TableCell>
          <TableCell>Amount</TableCell>
          <TableCell>Current Value</TableCell>
        </TableRow>
      </TableHead>
      <TableBody>
        {portfolio.length === 0 ? (
          <TableRow>
            <TableCell colSpan={4} align="center">No portfolios found</TableCell>
          </TableRow>
        ) : (
          portfolio.map(p => (
            <TableRow key={p.id} hover>
              <TableCell>{p.id}</TableCell>
              <TableCell>{p.name}</TableCell>
              <TableCell>${p.amount.toLocaleString()}</TableCell>
              <TableCell>${p.currentValue.toLocaleString()}</TableCell>
            </TableRow>
          ))
        )}
      </TableBody>
    </Table>
  </TableContainer>
);

export default PortfolioTable;
