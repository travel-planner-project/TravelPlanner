import { NavLink } from 'react-router-dom'
import { NavBarContent } from '../../router/routerData'
import styles from './Navbar.module.scss'
import NavbarProfile from './NavbarProfile'
import Icon from './Icon'

const ISLOGIN = true

function Navbar() {
  return (
    <div className={styles.container}>
      <ul>
        <NavbarProfile />
        {NavBarContent.map(navElement => {
          return (
            <li key={navElement.id}>
              <NavLink to={navElement.path}>
                {({ isActive }) => (
                  <div className={isActive ? `${styles.active}` : `${styles.inactive}`}>
                    {navElement.label}
                  </div>
                )}
              </NavLink>
            </li>
          )
        })}
      </ul>
      {ISLOGIN && (
        <button type='button' className={styles.logout}>
          <Icon name='box-arrow-left' size={24} />
          <span>logout</span>
        </button>
      )}
    </div>
  )
}

export default Navbar
