import { useRecoilValue } from 'recoil'
import { useEffect } from 'react'
import Navbar from '../components/Common/Navbar/Navbar'
import useRouter from '../hooks/useRouter'
import { userInfo } from '../store/store'
import style from './GeneralLayout.module.scss'

type GeneralLayoutProps = {
  children: React.ReactNode
  withAuth: boolean
}

function GeneralLayout({ children, withAuth }: GeneralLayoutProps) {
  const { pathname, routeTo } = useRouter()
  const { userId: isLogin } = useRecoilValue(userInfo)

  const redirectToLogin = () => {
    if (withAuth && !isLogin) {
      // 로그인 안된 경우 (userId가 undefined인 경우)
      routeTo('/user/login')
    }
  }

  useEffect(() => {
    // 로그인되어 있는지 && 로그인 필요한 페이지인지 확인
    redirectToLogin()
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [pathname, withAuth])

  return (
    <div className={style.container}>
      <Navbar />
      {children}
    </div>
  )
}

export default GeneralLayout
