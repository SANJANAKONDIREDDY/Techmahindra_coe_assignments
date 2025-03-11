import React, { useState, useEffect } from 'react';
import ChildComponent from './ChildComponent';
import axios from 'axios';

const ParentComponent = () => {
    const [data, setData] = useState([]);
    const [formData, setFormData] = useState('');

    // API Call using Axios
    useEffect(() => {
        axios.get('https://jsonplaceholder.typicode.com/posts')
            .then(response => setData(response.data.slice(0, 5)))
            .catch(error => console.error('Error fetching data:', error));
    }, []);

    // Form Submission
    const handleSubmit = (e) => {
        e.preventDefault();
        alert(`Form Submitted with data: ${formData}`);
    };

    return (
        <div className="container mt-5">
            <h1 className="text-center">Parent Component</h1>

            {/* Form Handling */}
            <form onSubmit={handleSubmit} className="my-3">
                <div className="form-group">
                    <label>Enter Your Name:</label>
                    <input
                        type="text"
                        className="form-control"
                        value={formData}
                        onChange={(e) => setFormData(e.target.value)}
                    />
                </div>
                <button type="submit" className="btn btn-primary mt-2">Submit</button>
            </form>

            {/* Display API Data */}
            <h3>API Data:</h3>
            <ul className="list-group">
                {data.map(post => (
                    <li key={post.id} className="list-group-item">
                        {post.title}
                    </li>
                ))}
            </ul>

            {/* Child Component */}
            <ChildComponent message="Hello from Parent!" />
        </div>
    );
};

export default ParentComponent;
