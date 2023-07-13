import Navbar from '../components/Common/Navbar'
import style from './GeneralLayout.module.scss'

type GeneralLayoutProps = {
  children: React.ReactNode
}

function GeneralLayout({ children }: GeneralLayoutProps) {
  return (
    <div className={style.container}>
      <Navbar />
      {children}
    </div>
  )
}

export default GeneralLayout
