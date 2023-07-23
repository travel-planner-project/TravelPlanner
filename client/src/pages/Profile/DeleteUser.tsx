import styles from './DeleteUser.module.scss'

function DeleteUser() {
  return (
    <div className={styles.entireContainer}>
      <h1>회원탈퇴 관련 안내</h1>
      <ul>
        <li>같은 이메일로 다시 가입할 수 없습니다.</li>
        <li>기록하신 모든 내용은 삭제됩니다.</li>
        <li>회원정보는 탈퇴 후 90일간 보관됩니다.</li>
      </ul>
      <form>
        <label htmlFor='password' className={styles.label}>
          비밀번호
          <input id='password' type='password' required />
        </label>
        <button type='submit' className={styles.confirmButton}>
          탈퇴하기
        </button>
      </form>
    </div>
  )
}

export default DeleteUser
