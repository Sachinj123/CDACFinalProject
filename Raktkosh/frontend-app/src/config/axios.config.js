import axios from 'axios';
import { api as baseURL} from '../application.json';

const instance = axios.create({ baseURL });

instance.interceptors.request.use(request => {
  request.headers.Authorization = "Bearer " + localStorage.getItem("token");
  return request;
});

export default instance;
