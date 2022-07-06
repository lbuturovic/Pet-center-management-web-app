import axios from '../api/axios';
import useAuth from './useAuth';

const useRefreshToken = () => {
    const { setAuth } = useAuth();

    const refresh = async () => {
        const response = await axios.post('/user/refresh-token',
        JSON.stringify({ refreshToken: localStorage.getItem('refreshToken') }),
        {
            headers: { 'Content-Type': 'application/json' }
        });
        setAuth(prev => {
            console.log(JSON.stringify(prev));
            console.log(response.data.accessToken);

            //role
            var token = JSON.stringify(response?.data);
            var base64Url = token.split('.')[1];
            var base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
            var jsonPayload = decodeURIComponent(atob(base64).split('').map(function (c) {
                return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
            }).join(''));
            const roles = JSON.parse(jsonPayload).role
            return {
                ...prev,
                roles: roles,
                accessToken: response.data.accessToken,
                uuid: response.data.uuid
            }
        });
        return response.data.accessToken;
    }
    return refresh;
};

export default useRefreshToken;