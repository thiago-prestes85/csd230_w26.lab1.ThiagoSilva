import { useState, useEffect } from 'react'
import './App.css'
import axiosInstance from './services/axiosConfig'

function App() {

    const [books, setBooks] = useState([])
    const [isAdmin, setIsAdmin] = useState(false)
    const [title, setTitle] = useState("")
    const [price, setPrice] = useState("")

    const [editingBook, setEditingBook] = useState(null)
    const [editTitle, setEditTitle] = useState("")
    const [editPrice, setEditPrice] = useState("")



    useEffect(() => {
        axiosInstance.get("/api/rest/books")
            .then(res => setBooks(res.data))
            .catch(err => console.error(err))
    }, [])

    const startEdit = (book) => {
        setEditingBook(book.id)
        setEditTitle(book.title)
        setEditPrice(book.pubPrice)
    }


    useEffect(() => {
        const token = localStorage.getItem("token")?.trim()
        console.log("TOKEN:", token)
        setIsAdmin(token === "admin-token")
    }, [])

    // DELETE FUNCTION
    const deleteBook = (id) => {
        axiosInstance.delete(`/api/rest/books/${id}`)
            .then(() => {
                setBooks(books.filter(book => book.id !== id))
            })
            .catch(err => console.error(err))
    }

    const addBook = () => {
        axiosInstance.post("/api/rest/books", {
            title: title,
            pubPrice: parseFloat(price)
        })
            .then(res => {
                setBooks([...books, res.data])
                setTitle("")
                setPrice("")
            })
            .catch(err => console.error(err))
    }

    const updateBook = (id) => {
        axiosInstance.put(`/api/rest/books/${id}`, {
            title: editTitle,
            pubPrice: parseFloat(editPrice)
        })
            .then(res => {
                setBooks(books.map(b => b.id === id ? res.data : b))
                setEditingBook(null)
            })
            .catch(err => console.error(err))
    }

    const btnStyle = {
        padding: "6px 10px",
        marginLeft: "5px",
        background: "#007bff",
        color: "white",
        border: "none",
        borderRadius: "5px",
        cursor: "pointer"
    }

    return (
        <div style={{ fontFamily: "Arial", background: "#f5f5f5", minHeight: "100vh" }}>

            {/* NAVBAR */}
            <div style={{
                background: "#333",
                color: "white",
                padding: "15px",
                textAlign: "center",
                fontSize: "20px"
            }}>
                My Book Store
            </div>

            {/* CONTENT */}
            <div style={{
                maxWidth: "800px",
                margin: "20px auto",
                background: "white",
                padding: "20px",
                borderRadius: "8px",
                boxShadow: "0 2px 5px rgba(0,0,0,0.1)"
            }}>

                <h1 style={{ textAlign: "center" }}>Book List</h1>

                <p style={{ textAlign: "center" }}>
                    isAdmin: <strong>{isAdmin ? "TRUE" : "FALSE"}</strong>
                </p>

                {isAdmin && (
                    <div style={{ marginBottom: "20px", textAlign: "center" }}>
                        <input
                            placeholder="Title"
                            value={title}
                            onChange={(e) => setTitle(e.target.value)}
                            style={{ marginRight: "10px", padding: "5px" }}
                        />

                        <input
                            placeholder="Price"
                            value={price}
                            onChange={(e) => setPrice(e.target.value)}
                            style={{ marginRight: "10px", padding: "5px" }}
                        />

                        <button style={btnStyle} onClick={addBook}>
                            Add Book
                        </button>
                    </div>
                )}

                <ul style={{ listStyle: "none", padding: 0 }}>
                    {books.length === 0 && (
                        <p style={{ textAlign: "center", color: "#888" }}>
                            No books available
                        </p>
                    )}

                    {books.map(book => (
                        <li key={book.id} style={{
                            display: "flex",
                            justifyContent: "space-between",
                            alignItems: "center",
                            padding: "10px",
                            borderBottom: "1px solid #ddd"
                        }}>

                            {editingBook === book.id ? (
                                <>
                                    <input
                                        value={editTitle}
                                        onChange={(e) => setEditTitle(e.target.value)}
                                    />
                                    <input
                                        value={editPrice}
                                        onChange={(e) => setEditPrice(e.target.value)}
                                    />
                                    <button onClick={() => updateBook(book.id)}>Save</button>
                                </>
                            ) : (
                                <span>
                {book.title} - ${book.pubPrice}
            </span>
                            )}

                            {isAdmin && (
                                <>
                                    <button onClick={() => startEdit(book)}>Edit</button>
                                    <button onClick={() => deleteBook(book.id)}>Delete</button>
                                </>
                            )}

                        </li>
                    ))}
                </ul>
            </div>
        </div>
    )
}

export default App