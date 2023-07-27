import styles from './RenderCheck.module.scss'

type RenderCheckProps = {
  onCurrentPasswordChange: (event: React.ChangeEvent<HTMLInputElement>) => void
  onCheckedSubmit: (event: React.FormEvent<HTMLFormElement>) => void
}

function RenderCheck({ onCurrentPasswordChange, onCheckedSubmit }: RenderCheckProps) {
  return (
    <form className={styles.entireContainer} onSubmit={onCheckedSubmit}>
      <label htmlFor='password' className={styles.label}>
        현재 비밀번호
        <input id='password' type='password' onChange={onCurrentPasswordChange} required />
      </label>
      <button type='submit' className={styles.confirmButton}>
        확인
      </button>
    </form>
  )
}

export default RenderCheck
