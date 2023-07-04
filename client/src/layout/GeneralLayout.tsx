import Navbar from '../components/Common/Navbar'
import style from './GeneralLayout.module.scss'

type GeneralLayoutProps = {
  children: React.ReactNode
  withAuth: boolean
}

function GeneralLayout({ children, withAuth }: GeneralLayoutProps) {
  return (
    <div className={style.container}>
      <Navbar />
      {children}
    </div>
  )
}

export default GeneralLayout
