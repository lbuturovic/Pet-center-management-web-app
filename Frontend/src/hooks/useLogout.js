import useAuth from "./useAuth";

const useLogout = () => {
    const {setAuth} = useAuth();

    const logout = () => {
        setAuth({});
        localStorage.setItem('refreshToken', "")
    }

    return logout;
}

export default useLogout