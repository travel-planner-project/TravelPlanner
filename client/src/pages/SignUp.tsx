import { useForm } from 'react-hook-form'
import { useState } from 'react'
import styles from './SignUp.module.scss'
import Email from '../components/SignUp/Email'
import Nickname from '../components/SignUp/Nickname'
import Password from '../components/SignUp/Password'
import PasswordCheck from '../components/SignUp/PasswordCheck'
import { ShowType, FormValueType } from '../types/SignUp'

function SignUp() {
  const [show, setShow] = useState<ShowType>({
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

  return (
    <form className={styles.signUpForm} onSubmit={() => handleSubmit}>
      <Email register={register} dirtyFields={dirtyFields} errors={errors} />
      <Nickname register={register} dirtyFields={dirtyFields} errors={errors} />
      <Password
        register={register}
        dirtyFields={dirtyFields}
        errors={errors}
        show={show}
        handleShow={handleShow}
      />
      <PasswordCheck
        register={register}
        dirtyFields={dirtyFields}
        errors={errors}
        getValues={getValues}
        show={show}
        handleShow={handleShow}
      />
      <button type='submit' className={styles.submitBtn} disabled={isSubmitting}>
        회원가입
      </button>
    </form>
  )
}

export default SignUp
