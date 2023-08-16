import { useRecoilValue } from 'recoil'
import { useEffect } from 'react'
import Navbar from '../components/Common/Navbar/Navbar'
import useRouter from '../hooks/useRouter'
import { userInfo } from '../store/store'
import style from './GeneralLayout.module.scss'

type GeneralLayoutProps = {
  children: React.ReactNode
  withAuth: boolean
  label: string
}

function GeneralLayout({ children, withAuth, label }: GeneralLayoutProps) {
  const { pathname, routeTo } = useRouter()
  const { userId: isLogin } = useRecoilValue(userInfo)

  const redirectToPage = () => {
    // 로그인 안하고 home 화면 접근
    if (withAuth && !isLogin && label === 'HOME') {
      routeTo('/feed')
      return
    }

    // 로그인 안된 경우 (userId가 undefined인 경우)
    if (withAuth && !isLogin) {
      routeTo('/user/login')
      return
    }
  }

  useEffect(() => {
    // 로그인되어 있는지 && 로그인 필요한 페이지인지 확인
    redirectToPage()
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
