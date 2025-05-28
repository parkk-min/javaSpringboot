import './App.css';
import {BrowserRouter, Route, Routes} from "react-router-dom";
import MainLayout from "./MainLayout";
import Login from "./Login";
import Admin from "./Admin";
import {useEffect} from "react";
import apiClient from "./apiInstance";
import {useDispatch} from "react-redux";
import {setToken} from "./store";

function App() {
    const dispatch = useDispatch();

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await apiClient.get("/csrf-token", {
                    withCredentials: true
                });
                // csrfToken은 get 요청에는 딱히 필요없다. post.put등 수정가능 한 항목만 해당
                dispatch(setToken(response.data["csrf-token"]));
                console.log(response.data["csrf-token"]);
            } catch (error) {

            }
        }

        fetchData();
    }, []);

    return (
        <>
            <BrowserRouter>
                <Routes>
                    <Route path={"/"} element={<MainLayout/>}>
                        <Route index element={<Login/>}></Route>
                        <Route path={"/admin"} element={<Admin/>}></Route>
                    </Route>
                </Routes>
            </BrowserRouter>
        </>
    );
}

export default App;
