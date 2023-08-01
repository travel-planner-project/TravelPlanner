import { useNavigate, useLocation, useParams } from 'react-router-dom'

const useRouter = () => {
  const navigate = useNavigate()
  const { pathname } = useLocation()
  const params = useParams()

  return {
    params,
    pathname,
    routeTo: navigate,
  }
}

export default useRouter
