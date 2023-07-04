import Navbar from '../components/Common/Navbar'

type GeneralLayoutProps = {
  children: React.ReactNode
  withAuth: boolean
}

function GeneralLayout({ children, withAuth }: GeneralLayoutProps) {
  return (
    <>
      {children}
      <Navbar />
    </>
  )
}

export default GeneralLayout
