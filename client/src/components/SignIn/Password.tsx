import { useState } from 'react'
import ShowPasswordButton from '../SignUp/ShowPasswordButton'
import styles from './Label.module.scss'

function Password() {
  const [showPassword, setShowPassword] = useState<boolean>(false)

  const handleShow = () => {
    setShowPassword(pre => !pre)
  }

  return (
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
        <ShowPasswordButton show={showPassword} handleShow={handleShow} />
      </div>
    </label>
  )
}

export default Password
