import axios from 'axios';

const api = axios.create({
    baseURL: 'http://localhost:8080',
    withCredentials: true,
});
export default api;

export const getPendingStudents = async () => {

    return await api.get(
        "/api/admin/pending-students"
    );
};

export const approveStudent = async (id) => {

    return await api.put(
        `/api/admin/approve/${id}`
    );
};