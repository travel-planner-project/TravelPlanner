import styles from './SignIn.module.scss'

function SignIn() {
  return (
    <form className={styles.container}>
      <label className={styles.label} htmlFor='email'>
        이메일
        <input id='email' type='email' placeholder='이메일을 입력해주세요' required />
      </label>

      <label className={styles.label} htmlFor='nickName'>
        닉네임
        <input id='nickName' type='text' placeholder='사용할 닉네임을 입력해주세요' required />
      </label>

      <label className={styles.label} htmlFor='password'>
        비밀번호
        <input
          id='password'
          type='password'
          placeholder='8~15자 이하 영문, 숫자, 특수문자 조합'
          required
        />
      </label>

      <label className={styles.label} htmlFor='password'>
        비밀번호 확인
        <input
          id='password'
          type='password'
          placeholder='비밀번호를 한번 더 입력해주세요'
          required
        />
      </label>

      <button type='submit'>회원가입</button>
    </form>
  )
}

export default SignIn
