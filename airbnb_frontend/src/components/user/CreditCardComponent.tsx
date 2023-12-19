import {
  Button,
  Container,
  Dialog,
  DialogActions,
  DialogContent,
  DialogTitle,
  Divider,
  MenuItem,
  Select,
  SelectChangeEvent,
  TextField,
} from "@mui/material";
import CreditCardTableComponent from "./CreditCardTableComponent";
import { ChangeEvent, ReactNode, useEffect, useState } from "react";
import { API_BASE_URL } from "../../api/apiConstants";
import axios from "axios";
import { CreditCard } from "../../domain/CreditCard";
import { User } from "../../domain/User";

interface PartitionsType {
  [partition: string]: CreditCard[];
}

const CreditCardComponent = () => {
  const [data, setData] = useState<CreditCard[]>([]);
  const [creditCardName, setCreditCardName] = useState<string>("");
  const [isModalOpen, setModalOpen] = useState<boolean>(false);
  const [creditCardNumber, setCreditCardNumber] = useState<string>("");
  const [users, setUsers] = useState<User[]>([]);
  const [user, setUser] = useState<User>(users[0]);
  const [expiryDate, setExpiryDate] = useState<Date>(new Date());
  const [partitions, setPartitions] = useState<PartitionsType>({});

  // false for saving inserting, true for updating
  const [isModalEdit, setModalEdit] = useState<boolean>(false);

  const emptyCreditCard = () => {
    setCreditCardNumber("");
    setCreditCardName("");
  };

  const fetchPartitions = async () => {
    try {
      const response = await axios.get<PartitionsType>(
        `${API_BASE_URL}creditCards/partitions`
      );
      setPartitions(response.data);
    } catch (error) {
      console.error("Error fetching data:", error);
    }
  };

  const handleInsert = async () => {
    const creditCard: CreditCard = {
      creditCardNumber,
      name: creditCardName,
      user,
      expiryDate,
    };
    try {
      await axios.post(`${API_BASE_URL}creditCards`, creditCard);
    } catch (error) {
      console.error("Error fetching data:", error);
    } finally {
      fetchData();
      fetchPartitions();
      emptyCreditCard();
      setModalOpen(false);
    }
  };

  const handleInsertWithUserName = async () => {
    const creditCard: CreditCard = {
      creditCardNumber,
      name: creditCardName,
      user,
      expiryDate,
    };
    try {
      await axios.post(`${API_BASE_URL}creditCards/withUserName`, creditCard);
    } catch (error) {
      console.error("Error fetching data:", error);
    } finally {
      fetchData();
      fetchPartitions();
      emptyCreditCard();
      setModalOpen(false);
    }
  };

  const fetchData = async () => {
    try {
      const response = await axios.get<CreditCard[]>(
        `${API_BASE_URL}creditCards`
      );
      setData(response.data);
    } catch (error) {
      console.error("Error fetching data:", error);
    }
  };

  const fetchUsers = async () => {
    try {
      const response = await axios.get<User[]>(`${API_BASE_URL}users`);
      setUsers(response.data);
    } catch (error) {
      console.error("Error fetching data:", error);
    }
  };

  useEffect(() => {
    fetchData();
    fetchUsers();
    fetchPartitions();
  }, []);

  const setModalInfo = (creditCardToEdit: CreditCard) => {
    setCreditCardNumber(creditCardToEdit.creditCardNumber);
    setCreditCardName(creditCardToEdit.name);
    setUser(creditCardToEdit.user);
    // TODO Fix date
    setExpiryDate(creditCardToEdit.expiryDate);
  };

  const openDialogForEdit = (creditCardToEdit: CreditCard) => {
    setModalOpen(true);
    setModalEdit(true);
    setModalInfo(creditCardToEdit);
  };

  const handleUpdate = async () => {
    const creditCard: CreditCard = {
      creditCardNumber,
      expiryDate,
      name: creditCardName,
      user,
    };
    try {
      await axios.patch(
        `${API_BASE_URL}creditCards/${creditCard.creditCardNumber}`,
        creditCard
      );
    } catch (error) {
      console.error("Error fetching data:", error);
    } finally {
      fetchData();
      fetchPartitions();
      emptyCreditCard();
      setModalOpen(false);
    }
  };

  const handleUpdateWithUserName = async () => {
    const creditCard: CreditCard = {
      creditCardNumber,
      expiryDate,
      name: creditCardName,
      user,
    };
    try {
      await axios.patch(
        `${API_BASE_URL}creditCards/${creditCard.creditCardNumber}/withUserName`,
        creditCard
      );
    } catch (error) {
      console.error("Error fetching data:", error);
    } finally {
      fetchData();
      fetchPartitions();
      emptyCreditCard();
      setModalOpen(false);
    }
  };

  const handleDelete = async (creditCard: CreditCard) => {
    try {
      await axios.delete(
        `${API_BASE_URL}creditCards/${creditCard.creditCardNumber}`
      );
    } catch (error) {
      console.error("Error fetching data:", error);
    }
    fetchData();
    fetchPartitions();
  };

  const handleConfirm = () => {
    isModalEdit ? handleUpdate() : handleInsert();
  };

  const handleConfirmWithUserName = () => {
    isModalEdit ? handleUpdateWithUserName() : handleInsertWithUserName();
  };

  const handleUserSelection = (
    event: SelectChangeEvent<number>,
    child: ReactNode
  ) => {
    const chosen: User | undefined = users.find(
      (u) => u.userId === event.target.value
    );
    if (chosen) {
      setUser(chosen);
    }
  };

  const handleExpiryDateChange = (event: ChangeEvent<HTMLInputElement>) => {
    setExpiryDate(new Date(event.target.value));
  };

  return (
    <Container>
      <Button
        onClick={() => {
          setModalOpen(true);
          setModalEdit(false);
          emptyCreditCard();
        }}
      >
        Insert new Credit Card
      </Button>
      <Dialog open={isModalOpen} onClose={() => setModalOpen(false)}>
        <DialogTitle>Insert New Credit Card</DialogTitle>
        <DialogContent>
          <TextField
            label="CreditCard Number"
            value={creditCardNumber}
            disabled={isModalEdit}
            onChange={(e) => setCreditCardNumber(e.target.value)}
          />
          <TextField
            label="User Name"
            value={creditCardName}
            onChange={(e) => setCreditCardName(e.target.value)}
          />
          <Select
            value={user?.userId}
            label="User"
            onChange={handleUserSelection}
          >
            {users.map((u) => (
              <MenuItem value={u.userId} key={u.userId}>
                {u.name}
              </MenuItem>
            ))}
          </Select>
          <TextField
            label="Expiry Date"
            type="date"
            //TODO fix date
            onChange={handleExpiryDateChange}
            InputLabelProps={{
              shrink: true,
            }}
          />
        </DialogContent>
        <DialogActions>
          <Button onClick={() => setModalOpen(false)} color="primary">
            Cancel
          </Button>
          <Button onClick={handleConfirm} color="primary">
            {isModalEdit ? "Update" : "Insert"}
          </Button>
          <Button onClick={handleConfirmWithUserName} color="primary">
            {isModalEdit ? "Update with user name" : "Insert with user name"}
          </Button>
        </DialogActions>
      </Dialog>
      <CreditCardTableComponent
        data={data}
        handleDelete={handleDelete}
        openDialogForEdit={openDialogForEdit}
        header="ALL"
      />
      <Divider />
      {Object.entries(partitions).map(([partition, creditCards]) => (
        <CreditCardTableComponent
          key={partition}
          data={creditCards}
          handleDelete={handleDelete}
          openDialogForEdit={openDialogForEdit}
          header={partition}
        />
      ))}
    </Container>
  );
};

export default CreditCardComponent;
