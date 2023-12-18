import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import "./App.css";
import PropertyDetailsComponent from "./components/property/PropertyDetailsComponent";
import LoginComponent from "./components/auth/LoginComponent";
import PropertyListComponent from "./components/property/PropertyListComponent";
import AmenityComponent from "./components/amenity/AmenityComponent";

function App() {
  return (
    <Router>
      <div className="App">
        <div className="body">
          <Routes>
            <Route path="/" element={<LoginComponent />} />
            <Route path="properties" element={<PropertyListComponent />} />
            <Route
              path="properties/:propertyId"
              element={<PropertyDetailsComponent />}
            />
            <Route path="amenities" element={<AmenityComponent />} />
          </Routes>
        </div>
      </div>
    </Router>
  );
}

export default App;
