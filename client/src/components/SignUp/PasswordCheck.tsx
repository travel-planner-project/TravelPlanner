import { PasswordCheckProps } from '../../types/SignUp'
import styles from './form.module.scss'
import ShowPasswordButton from './ShowPasswordButton'

function PasswordCheck({
  register,
  dirtyFields,
  errors,
  getValues,
  show,
  handleShow,
}: PasswordCheckProps) {
  const {
    onChange: passwordCheckOnChange,
    onBlur: passwordCheckOnBlur,
    name: passwordCheckName,
    ref: passwordCheckRef,
  } = register('passwordCheck', {
    required: '비밀번호를 확인해주세요.',
    validate: {
      matchesPassword: (value: string) =>
        value === getValues('password') || '비밀번호가 일치하지 않습니다.',
    },
  })

  return (
    <label className={styles.label} htmlFor='passwordCheck'>
      비밀번호 확인
      <div className={styles.inputErrorBox}>
        <input
          id='passwordCheck'
          type={show.passwordCheck ? 'text' : 'password'}
          name={passwordCheckName}
          onChange={passwordCheckOnChange}
          onBlur={passwordCheckOnBlur}
          ref={passwordCheckRef}
          placeholder='비밀번호를 한번 더 입력해주세요'
          required
        />
        <ShowPasswordButton show={show} handleShow={handleShow} labelName='passwordCheck' />
        <span className={styles.errorMessage}>
          {dirtyFields.passwordCheck && errors.passwordCheck && errors.passwordCheck.message}
        </span>
      </div>
    </label>
  )
}

export default PasswordCheck
