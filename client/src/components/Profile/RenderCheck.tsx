import styles from './RenderCheck.module.scss'

function RenderCheck() {
  return (
    <form className={styles.entireContainer}>
      <label htmlFor='password' className={styles.label}>
        현재 비밀번호
        <input id='password' type='password' required />
      </label>
      <button type='submit' className={styles.confirmButton}>
        확인
      </button>
    </form>
  )
}

export default RenderCheck
