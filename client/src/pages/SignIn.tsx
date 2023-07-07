import { useState } from 'react'
import { Link } from 'react-router-dom'
import ShowPasswordButton from '../components/SignUp/ShowPasswordButton'
import styles from './SignIn.module.scss'

function SignIn() {
  const [showPassword, setShowPassword] = useState<boolean>(false)

  const handleShow = () => {
    setShowPassword(pre => !pre)
  }

  return (
    <form className={styles.signInForm}>
      <label htmlFor='email' className={styles.label}>
        이메일
        <input id='email' type='email' name='email' placeholder='example@example.com' required />
      </label>

      <label htmlFor='password' className={styles.label}>
        비밀번호
        <div className={styles.passwordInput}>
          <input
            id='password'
            type={showPassword ? 'text' : 'password'}
            name='password'
            placeholder='비밀번호를 입력해주세요'
            required
          />
          <ShowPasswordButton show={showPassword} handleShow={handleShow} labelName='password' />
        </div>
      </label>

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
