import { NavLink } from 'react-router-dom'
import { NavBarContent } from '../../router/routerData'

function Navbar() {
  return (
    <div className='container text-center bg-light bg-tertiary-color text-secondary'>
      <ul>
        {/* 프로필 네비
         <li>
          <NavLink to='/'>
            <img src='' alt='profile' />
          </NavLink>
        </li> */}

        {NavBarContent.map(navElement => {
          return (
            <li key={navElement.id}>
              <NavLink to={navElement.path}>
                <div>{navElement.label}</div>
              </NavLink>
            </li>
          )
        })}
      </ul>
    </div>
  )
}

export default Navbar
