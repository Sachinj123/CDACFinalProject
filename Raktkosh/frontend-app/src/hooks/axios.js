import { useState, useEffect } from 'react';
import axios from '../config/axios.config';

const useAxios = config => {
  const [ error, setError ] = useState(null);
  const [ waiting, setWaiting ] = useState(false);
  const [ response, setResponse ] = useState(null);

  useEffect(() => {
    const fetchData = () => {
      setWaiting(true);
      axios(config)
        .then(res => {
          setResponse(res.data);
          setError(null);
        })
        .catch(err => setError(err))
        .finally(() => setWaiting(false));
    };
    fetchData();
  }, [config]);
  return [ response, error, waiting ];
};

export default useAxios;
