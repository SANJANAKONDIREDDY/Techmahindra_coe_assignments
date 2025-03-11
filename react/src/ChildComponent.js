import React, { useState } from 'react';

function ChildComponent({ data, onDelete, onUpdate }) {
  const [editData, setEditData] = useState({ id: '', name: '', email: '', phone: '', patientNo: '', gender: '', photo: '' });
  const [isEditing, setIsEditing] = useState(false);

  const handleEdit = (user) => {
    setEditData(user);
    setIsEditing(true);
  };

  const handleSave = () => {
    onUpdate(editData.id, editData);
    setIsEditing(false);
    setEditData({ id: '', name: '', email: '', phone: '', patientNo: '', gender: '', photo: '' });
  };

  return (
    <div className="row mt-5">
      {data.map(user => (
        <div className="col-md-4 mb-4" key={user.id}>
          <div className="card shadow-sm">
            <div className="card-body">
              <h5 className="card-title">{user.name}</h5>
              <p className="card-text">Email: {user.email}</p>
              <p className="card-text">Phone: {user.phone}</p>
              <p className="card-text">Patient No: {user.patientNo}</p>
              <p className="card-text">Gender: {user.gender}</p>
              {user.photo && <img src={user.photo} alt="Patient" className="img-fluid rounded" />}
              <button className="btn btn-danger btn-sm me-2" onClick={() => onDelete(user.id)}>Delete</button>
              <button className="btn btn-warning btn-sm" onClick={() => handleEdit(user)}>Edit</button>
            </div>
          </div>
        </div>
      ))}
    </div>
  );
}

export default ChildComponent;
