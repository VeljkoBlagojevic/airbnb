import { Link } from "react-router-dom";
import { Button } from "@mui/material";
import "./Navbar.css";
import { FC } from "react";

const NavbarComponent: FC = () => {
  return (
    <div className="navbar1">
      <div className="navbar-elements">
        <Link to="/properties">
          <Button className="btn-navbar">Properties</Button>
        </Link>
        <Link to="/propertyCategories">
          <Button className="btn-navbar">Property Categories</Button>
        </Link>
        <Link to="/amenities">
          <Button className="btn-navbar">Amenities</Button>
        </Link>
        <Link to="/users">
          <Button className="btn-navbar">Users</Button>
        </Link>
        <Link to="/creditCards">
          <Button className="btn-navbar">Credit Cards</Button>
        </Link>
        <Link to="/reviews">
          <Button className="btn-navbar">Reviews</Button>
        </Link>
      </div>
    </div>
  );
};

export default NavbarComponent;
