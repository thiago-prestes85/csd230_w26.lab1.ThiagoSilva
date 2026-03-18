import { useState, useEffect } from 'react'
import './App.css'
import axiosInstance from './services/axiosConfig'

function App() {

    const [books, setBooks] = useState([])
    const [isAdmin, setIsAdmin] = useState(false)

    useEffect(() => {
        axiosInstance.get("/rest/books")
            .then(res => setBooks(res.data))
            .catch(err => console.error(err))
    }, [])

    useEffect(() => {
        const token = localStorage.getItem("token")?.trim();

        console.log("TOKEN:", token);

        setIsAdmin(token === "admin-token");
    }, [])

    return (
        <div>
            <h1>Book List</h1>

            <p>isAdmin: {isAdmin ? "TRUE" : "FALSE"}</p>

            <ul>
                {books.map(book => (
                    <li key={book.id}>
                        {book.title} - ${book.pubPrice}

                        {isAdmin && (
                            <div>
                                <button>Edit</button>
                                <button>Delete</button>
                            </div>
                        )}
                    </li>
                ))}
            </ul>
        </div>
    )
}

export default App