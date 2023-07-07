import { useForm } from 'react-hook-form'
import Email from '../components/SignUp/Email'
import Nickname from '../components/SignUp/Nickname'
import Password from '../components/SignUp/Password'
import PasswordCheck from '../components/SignUp/PasswordCheck'
import { FormValueType, SignUpViewProps } from '../types/SignUp'
import styles from './SignUp.module.scss'

function SingUpView({
  register,
  handleSubmit,
  dirtyFields,
  errors,
  getValues,
  isSubmitting,
}: SignUpViewProps) {
  return (
    <form className={styles.signUpForm} onSubmit={() => handleSubmit}>
      <Email register={register} dirtyFields={dirtyFields} errors={errors} />
      <Nickname register={register} dirtyFields={dirtyFields} errors={errors} />
      <Password register={register} dirtyFields={dirtyFields} errors={errors} />
      <PasswordCheck
        register={register}
        dirtyFields={dirtyFields}
        errors={errors}
        getValues={getValues}
      />
      <button type='submit' className={styles.submitBtn} disabled={isSubmitting}>
        회원가입
      </button>
    </form>
  )
}

function SignUp() {
  const {
    register,
    handleSubmit,
    formState: { dirtyFields, errors, isSubmitting },
    getValues,
  } = useForm<FormValueType>({ mode: 'onChange' })

  const props = {
    register,
    handleSubmit,
    dirtyFields,
    errors,
    getValues,
    isSubmitting,
  }

  return <SingUpView {...props} />
}

export default SignUp
