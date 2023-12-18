import React, { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { Alert, Button, Snackbar, TextField } from "@mui/material";

const LoginComponent = () => {
  const navigate = useNavigate();

  const [error, setError] = useState<string>("");

  const handleCloseError = () => {
    setError("");
  };

  useEffect(() => {
    localStorage.removeItem("role");
    localStorage.removeItem("token");
  }, []);

  const login = async () => {
    const username = (document.getElementById("username") as HTMLInputElement)
      .value;
    const password = (document.getElementById("password") as HTMLInputElement)
      .value;

    try {
      const response = await axios.post(
        "http://localhost:8080/api/v1/auth/login",
        {
          username,
          password,
        }
      );

      localStorage.setItem("role", response.data.role);
      navigate("/properties/1");
    } catch (error: any) {
      console.error(error);
      setError(error.response.data.body.detail);
    }
  };

  return (
    <div className="login-form">
      <div className="form-group">
        <TextField
          required
          type="text"
          className="form-control"
          placeholder="User Name"
          id="username"
        />
      </div>
      <div className="form-group">
        <TextField
          required
          type="password"
          className="form-control"
          placeholder="Password"
          id="password"
        />
      </div>
      <div className="btn-container">
        <Button type="submit" className="btn-login" id="login" onClick={login}>
          Login
        </Button>

        <Button onClick={() => navigate("/properties")}>
          Check out properties listed!
        </Button>
      </div>
      <Snackbar
        open={error !== ""}
        autoHideDuration={250000}
        onClose={handleCloseError}
      >
        <Alert onClose={handleCloseError} severity="error">
          {error}
        </Alert>
      </Snackbar>
    </div>
  );
};

export default LoginComponent;
