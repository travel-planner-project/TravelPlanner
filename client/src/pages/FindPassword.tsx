import { findPassword } from '../apis/user'
import Email from '../components/SignIn/Email'
import styles from './FindPassword.module.scss'

const FindPassword = () => {
  const submitFindPassword = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault()

    const formData = new FormData(event.currentTarget)
    const email = formData.get('email')
    const response = await findPassword(email)
  }

  return (
    <form className={styles.container} onSubmit={submitFindPassword}>
      <Email />
      <div className={styles.text}>
        로그인 이메일을 입력해주세요. <br />
        해당 메일로 비밀번호 재설정 링크가 전송됩니다.
      </div>
      <button type='submit' className={styles.submitBtn}>
        이메일 전송
      </button>
    </form>
  )
}

export default FindPassword
