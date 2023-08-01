import { useState } from 'react'
import { useRecoilValue } from 'recoil'
import { userInfo } from '../../store/store'
import useLogout from '../../hooks/useLogout'
import useRouter from '../../hooks/useRouter'
import { deleteUser } from '../../apis/user'
import styles from './DeleteUser.module.scss'

function DeleteUser() {
  const { routeTo } = useRouter()
  const logout = useLogout()
  const { userId } = useRecoilValue(userInfo)
  const [currentPassword, setCurrentPassword] = useState('')

  const handlePasswordChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setCurrentPassword(event.target.value)
  }

  const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault()
    deleteUser({ userId: userId, password: currentPassword }).then(response => {
      if (response?.status === 200) {
        alert('회원탈퇴가 완료되었습니다.')
        // 체크박스 추가
        logout
        routeTo('/')
      } else if (response?.status === 400) {
        alert('비밀번호가 일치하지 않습니다.')
      } else {
        alert('회원 탈퇴에 실패했습니다. 자세한 내용은 관리자에게 문의해 주시기 바랍니다.')
      }
    })
  }

  return (
    <div className={styles.entireContainer}>
      <h1>회원탈퇴 관련 안내</h1>
      <ul>
        <li>같은 이메일로 다시 가입할 수 없습니다.</li>
        <li>기록하신 모든 내용은 삭제됩니다.</li>
        <li>회원정보는 탈퇴 후 90일간 보관됩니다.</li>
      </ul>
      <form onSubmit={handleSubmit}>
        <label htmlFor='password' className={styles.label}>
          비밀번호
          <input id='password' type='password' onChange={handlePasswordChange} required />
        </label>
        <button type='submit' className={styles.confirmButton}>
          탈퇴하기
        </button>
      </form>
    </div>
  )
}

export default DeleteUser
