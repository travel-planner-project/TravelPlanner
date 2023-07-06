import { useForm } from 'react-hook-form'
import { useState } from 'react'
import styles from './SignUp.module.scss'
import ShowPasswordButton from '../components/SignUp/showPasswordButton'

type FormValueType = {
  email: string
  nickname: string
  password: string
  passwordCheck: string
}

const emailReg = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/
const specialCharacterReg = /^[가-힣ㄱ-ㅎㅏ-ㅣa-zA-Z0-9]+$/
const passwordReg =
  // eslint-disable-next-line no-useless-escape
  /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[~!@#$%^&*(),.?`'":{}|<>_+\-=\[\]])[a-zA-Z0-9~!@#$%^&*(),.?`'":{}|<>_+\-=\[\]]+$/

function SignUp() {
  const [show, setShow] = useState({
    password: false,
    passwordCheck: false,
  })

  const handleShow = (type: 'password' | 'passwordCheck') => {
    setShow(pre => {
      return { ...pre, [type]: !pre[type] }
    })
  }

  const {
    register,
    handleSubmit,
    formState: { dirtyFields, errors, isSubmitting },
    getValues,
  } = useForm<FormValueType>({ mode: 'onChange' })

  const {
    onChange: emailOnChange,
    onBlur: emailOnBlur,
    name: emailName,
    ref: emailRef,
  } = register('email', {
    pattern: {
      value: emailReg,
      message: '이메일 형식이 올바르지 않습니다.',
    },
    required: '이메일은 필수값입니다.',
  })

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

  const {
    onChange: passwordOnChange,
    onBlur: passwordOnBlur,
    name: passwordName,
    ref: passwordRef,
  } = register('password', {
    pattern: {
      value: passwordReg,
      message: '영소문자, 숫자, 특수문자가 각각 한 개 이상 포함되어야 합니다.',
    },
    minLength: {
      value: 8,
      message: '비밀번호는 총 8자 이상이어야 합니다.',
    },
    maxLength: {
      value: 15,
      message: '비밀번호는 총 15자 이하여야 합니다.',
    },
    required: '비밀번호는 필수값입니다.',
  })

  const {
    onChange: passwordCheckOnChange,
    onBlur: passwordCheckOnBlur,
    name: passwordCheckName,
    ref: passwordCheckRef,
  } = register('passwordCheck', {
    required: '비밀번호를 확인해주세요.',
    validate: {
      matchesPassword: value => value === getValues('password') || '비밀번호가 일치하지 않습니다.',
    },
  })

  return (
    <form className={styles.signUpForm} onSubmit={() => handleSubmit}>
      <label className={styles.label} htmlFor='email'>
        이메일
        <div className={styles.inputErrorBox}>
          <input
            id='email'
            type='email'
            name={emailName}
            onChange={emailOnChange}
            onBlur={emailOnBlur}
            ref={emailRef}
            placeholder='이메일을 입력해주세요'
            required
          />
          {dirtyFields.email && errors.email && (
            <span className={styles.errorMessage}>{errors.email.message}</span>
          )}
        </div>
      </label>

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

      <label className={styles.label} htmlFor='password'>
        비밀번호
        <div className={styles.inputErrorBox}>
          <input
            id='password'
            type={show.password ? 'text' : 'password'}
            name={passwordName}
            onChange={passwordOnChange}
            onBlur={passwordOnBlur}
            ref={passwordRef}
            placeholder='8~15자 이하 영문, 숫자, 특수문자 조합'
            required
          />
          <ShowPasswordButton show={show} handleShow={handleShow} labelName='password' />
          <span className={styles.errorMessage}>
            {dirtyFields.password && errors.password && errors.password.message}
          </span>
        </div>
      </label>

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

      <button type='submit' className={styles.submitBtn} disabled={isSubmitting}>
        회원가입
      </button>
    </form>
  )
}

export default SignUp
