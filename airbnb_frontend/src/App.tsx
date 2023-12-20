import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import "./App.css";
import PropertyDetailsComponent from "./components/property/PropertyDetailsComponent";
import PropertyListComponent from "./components/property/PropertyListComponent";
import AmenityComponent from "./components/amenity/AmenityComponent";
import CreditCardComponent from "./components/user/CreditCardComponent";
import UserComponent from "./components/user/UserComponent";
import PropertyCategoryComponent from "./components/property/PropertyCategoryComponent";
import NavbarComponent from "./navbar/NavbarComponent";
import ReviewComponent from "./components/review/ReviewComponent";
import RatingComponent from "./components/review/RatingComponent";

function App() {
  return (
    <Router>
      <div className="App">
        <div className="body">
          <NavbarComponent />
          <Routes>
            <Route path="/" element={<PropertyListComponent />} />
            <Route path="properties" element={<PropertyListComponent />} />
            <Route
              path="properties/:propertyId"
              element={<PropertyDetailsComponent />}
            />
            <Route
              path="propertyCategories"
              element={<PropertyCategoryComponent />}
            />
            <Route path="amenities" element={<AmenityComponent />} />
            <Route path="creditCards" element={<CreditCardComponent />} />
            <Route path="users" element={<UserComponent />} />
            <Route path="reviews" element={<ReviewComponent />} />
            <Route
              path="reviews/:reviewId/ratings"
              element={<RatingComponent />}
            />
          </Routes>
        </div>
      </div>
    </Router>
  );
}

export default App;
