import {
  Alert,
  Button,
  Container,
  Dialog,
  DialogActions,
  DialogContent,
  DialogTitle,
  FormControl,
  Snackbar,
  TextField,
} from "@mui/material";
import UserTableComponent from "./UserTableComponent";
import { ChangeEvent, useEffect, useState } from "react";
import { API_BASE_URL } from "../../api/apiConstants";
import axios from "axios";
import { User } from "../../domain/User";

const UserComponent = () => {
  const [isModalOpen, setModalOpen] = useState<boolean>(false);
  const [userId, setUserId] = useState<number>(0);
  const [userName, setUserName] = useState<string>("");
  const [email, setEmail] = useState<string>("");
  const [gender, setGender] = useState<string>("");
  const [users, setUsers] = useState<User[]>([]);
  const [newGender, setNewGender] = useState<string>("");

  // false for saving inserting, true for updating
  const [isModalEdit, setModalEdit] = useState<boolean>(false);

  const [errorMessage, setErrorMessage] = useState<string>("");
  const [openSnackbar, setOpenSnackbar] = useState<boolean>(false);

  const handleCloseSnackbar = () => {
    setOpenSnackbar(false);
    setErrorMessage("");
  };

  const emptyUser = () => {
    setUserId(0);
    setUserName("");
    setEmail("");
    setGender("");
  };

  const handleInsert = async () => {
    const user: User = {
      userId,
      name: userName,
      email,
      gender,
    };
    try {
      await axios.post(`${API_BASE_URL}users`, user);
    } catch (error: any) {
      setErrorMessage(error.response.data.body.detail);
      setOpenSnackbar(true);
      console.error("Error fetching data:", error);
    } finally {
      fetchUsers();
      emptyUser();
      setModalOpen(false);
    }
  };

  const fetchUsers = async () => {
    try {
      const response = await axios.get<User[]>(`${API_BASE_URL}users`);
      setUsers(response.data);
    } catch (error: any) {
      setErrorMessage(error.response.data.body.detail);
      setOpenSnackbar(true);
      console.error("Error fetching data:", error);
    }
  };

  useEffect(() => {
    fetchUsers();
    fetchUsers();
  }, []);

  const setModalInfo = (userToEdit: User) => {
    setUserId(userToEdit.userId);
    setUserName(userToEdit.name);
    setEmail(userToEdit.email);
    setGender(userToEdit.gender);
  };

  const openDialogForEdit = (userToEdit: User) => {
    setModalOpen(true);
    setModalEdit(true);
    setModalInfo(userToEdit);
  };

  const handleUpdate = async () => {
    const user: User = {
      userId,
      name: userName,
      email,
      gender,
    };
    try {
      await axios.patch(`${API_BASE_URL}users/${user.userId}`, user);
    } catch (error: any) {
      setErrorMessage(error.response.data.body.detail);
      setOpenSnackbar(true);
      console.error("Error fetching data:", error);
    } finally {
      fetchUsers();
      emptyUser();
      setModalOpen(false);
    }
  };

  const handleDelete = async (user: User) => {
    try {
      await axios.delete(`${API_BASE_URL}users/${user.userId}`);
      fetchUsers();
    } catch (error: any) {
      setErrorMessage(error.response.data.body.detail);
      setOpenSnackbar(true);
      console.error("Error fetching data:", error);
    }
  };

  const handleConfirm = () => {
    isModalEdit ? handleUpdate() : handleInsert();
  };

  const handleNewGender = (event: ChangeEvent<HTMLInputElement>) => {
    setNewGender(event.target.value);
  };

  const addNewGender = async () => {
    try {
      await axios.post(`${API_BASE_URL}users/newGender/${newGender}`);
    } catch (error: any) {
      setErrorMessage(error.response.data.body.detail);
      setOpenSnackbar(true);
      console.error("Error fetching data:", error);
    }
  };

  return (
    <Container>
      <Button
        onClick={() => {
          setModalOpen(true);
          setModalEdit(false);
          emptyUser();
        }}
      >
        Insert new User
      </Button>
      <Dialog open={isModalOpen} onClose={() => setModalOpen(false)}>
        <DialogTitle>Insert New User</DialogTitle>
        <DialogContent>
          <TextField
            label="User ID"
            value={userId}
            disabled
            onChange={(e) => setUserId(parseInt(e.target.value))}
          />
          <TextField
            label="User Name"
            value={userName}
            onChange={(e) => setUserName(e.target.value)}
          />
          <TextField
            label="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
          />

          <TextField
            label="gender"
            value={gender}
            onChange={(e) => setGender(e.target.value)}
          />
        </DialogContent>
        <DialogActions>
          <Button onClick={() => setModalOpen(false)} color="primary">
            Cancel
          </Button>
          <Button onClick={handleConfirm} color="primary">
            {isModalEdit ? "Update" : "Insert"}
          </Button>
        </DialogActions>
      </Dialog>
      <UserTableComponent
        data={users}
        handleDelete={handleDelete}
        openDialogForEdit={openDialogForEdit}
      />

      <FormControl>
        <TextField
          label={`New gender`}
          value={newGender}
          onChange={handleNewGender}
          variant="outlined"
        />
        <Button onClick={addNewGender}>ADD NEW GENDER</Button>
      </FormControl>
      <Snackbar
        open={openSnackbar}
        autoHideDuration={6000}
        onClose={handleCloseSnackbar}
      >
        <Alert
          onClose={handleCloseSnackbar}
          severity="error"
          sx={{ width: "100%" }}
        >
          <strong>Error:</strong> {errorMessage}
        </Alert>
      </Snackbar>
    </Container>
  );
};

export default UserComponent;
