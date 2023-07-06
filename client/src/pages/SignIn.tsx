import { Link } from 'react-router-dom'
import styles from './SignIn.module.scss'

function SignIn() {
  return (
    <form className={styles.signInForm}>
      <label htmlFor='email' className={styles.label}>
        이메일
        <input id='email' type='email' name='email' placeholder='example@example.com' required />
      </label>

      <label htmlFor='password' className={styles.label}>
        비밀번호
        <input
          id='password'
          type='password'
          name='password'
          placeholder='비밀번호를 입력해주세요'
          required
        />
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
