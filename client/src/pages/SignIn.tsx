import { Link } from 'react-router-dom'
import styles from './SignIn.module.scss'
import Password from '../components/SignIn/Password'
import Email from '../components/SignIn/Email'

function SignIn() {
  return (
    <form className={styles.signInForm}>
      <Email />
      <Password />
      <div className={styles.btnBox}>
        <button type='submit' className={styles.signInBtn}>
          로그인
        </button>
        <Link to='/user/register'>회원가입</Link>
        {/* <Link to='/findpassword'>비밀번호 찾기</Link> */}
      </div>
    </form>
  )
}

export default SignIn
