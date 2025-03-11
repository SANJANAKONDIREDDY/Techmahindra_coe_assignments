import React, { useState, useEffect } from 'react';
import axios from 'axios';
import 'bootstrap/dist/css/bootstrap.min.css';
import './App.css';
import ChildComponent from './ChildComponent';

function App() {
  const [data, setData] = useState([]);
  const [formData, setFormData] = useState({ name: '', email: '', phone: '', patientNo: '', gender: '', photo: '' });

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handlePhotoUpload = (e) => {
    const file = e.target.files[0];
    const reader = new FileReader();
    reader.onloadend = () => {
      setFormData({ ...formData, photo: reader.result });
    };
    if (file) {
      reader.readAsDataURL(file);
    }
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    const newUser = {
      id: Date.now(),
      name: formData.name,
      email: formData.email,
      phone: formData.phone,
      patientNo: formData.patientNo,
      gender: formData.gender,
      photo: formData.photo
    };
    setData([...data, newUser]);
    setFormData({ name: '', email: '', phone: '', patientNo: '', gender: '', photo: '' });
  };

  const handleDelete = (id) => {
    setData(data.filter(user => user.id !== id));
  };

  const handleUpdate = (id, newData) => {
    setData(data.map(user => user.id === id ? { ...user, ...newData } : user));
  };

  useEffect(() => {
    axios.get('https://jsonplaceholder.typicode.com/users')
      .then(response => setData(response.data.map(user => ({
        id: user.id,
        name: user.name,
        email: user.email,
        phone: user.phone,
        patientNo: `P-${Math.floor(Math.random()*1000)}`,
        gender: 'Not specified',
        photo: ''
      }))))
      .catch(error => console.error('Error:', error));
  }, []);

  return (
    <div className="container my-5">
      <h2 className="text-center mb-4">Dental Clinic Management</h2>

      <div className="form-container p-4 shadow rounded bg-light">
        <h4>Add Patient Details</h4>
        <form onSubmit={handleSubmit}>
          <div className="mb-3">
            <label>Name</label>
            <input type="text" name="name" className="form-control" value={formData.name} onChange={handleChange} />
          </div>
          <div className="mb-3">
            <label>Email</label>
            <input type="email" name="email" className="form-control" value={formData.email} onChange={handleChange} />
          </div>
          <div className="mb-3">
            <label>Phone</label>
            <input type="text" name="phone" className="form-control" value={formData.phone} onChange={handleChange} />
          </div>
          <div className="mb-3">
            <label>Patient No</label>
            <input type="text" name="patientNo" className="form-control" value={formData.patientNo} onChange={handleChange} />
          </div>
          <div className="mb-3">
            <label>Gender</label>
            <select name="gender" className="form-control" value={formData.gender} onChange={handleChange}>
              <option value="">Select Gender</option>
              <option value="Male">Male</option>
              <option value="Female">Female</option>
              <option value="Others">Others</option>
            </select>
          </div>
          <div className="mb-3">
            <label>Upload Photo</label>
            <input type="file" className="form-control" onChange={handlePhotoUpload} />
          </div>
          <button type="submit" className="btn btn-primary w-100">Add Patient</button>
        </form>
      </div>

      <ChildComponent data={data} onDelete={handleDelete} onUpdate={handleUpdate} />
    </div>
  );
}

export default App;