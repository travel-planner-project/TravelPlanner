import { ReactHookFormProps } from '../../types/signUpTypes'
import styles from './Form.module.scss'

const specialCharacterReg = /^[가-힣ㄱ-ㅎㅏ-ㅣa-zA-Z0-9]+$/
function Nickname({ register, dirtyFields, errors }: ReactHookFormProps) {
  const {
    onChange: nicknameOnChange,
    onBlur: nicknameOnBlur,
    name: nicknameName,
    ref: nicknameRef,
  } = register('nickname', {
    pattern: {
      value: specialCharacterReg,
      message: '닉네임에는 한글, 영문, 숫자만 가능합니다.',
    },
    maxLength: {
      value: 16,
      message: '닉네임은 16자 이하여야 합니다.',
    },
    required: '닉네임은 필수값입니다.',
  })

  return (
    <label className={styles.label} htmlFor='nickname'>
      닉네임
      <div className={styles.inputErrorBox}>
        <input
          id='nickname'
          type='text'
          name={nicknameName}
          onChange={nicknameOnChange}
          onBlur={nicknameOnBlur}
          ref={nicknameRef}
          placeholder='사용할 닉네임을 입력해주세요'
          required
        />
        {dirtyFields.nickname && errors.nickname && (
          <span className={styles.errorMessage}>{errors.nickname.message}</span>
        )}
      </div>
    </label>
  )
}

export default Nickname
