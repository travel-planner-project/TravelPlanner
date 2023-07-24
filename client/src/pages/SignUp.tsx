import { useForm } from 'react-hook-form'
import Email from '../components/SignUp/Email'
import Nickname from '../components/SignUp/Nickname'
import Password from '../components/SignUp/Password'
import PasswordCheck from '../components/SignUp/PasswordCheck'
import { FormValueType, SignUpViewProps } from '../types/signUpTypes'
import styles from './SignUp.module.scss'
import { signUp } from '../apis/user'
import useRouter from '../hooks/useRouter'

function SingUpView({
  register,
  onSubmit,
  dirtyFields,
  errors,
  getValues,
  isSubmitting,
}: SignUpViewProps) {
  return (
    <form className={styles.signUpForm} onSubmit={onSubmit}>
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
  const { routeTo } = useRouter()
  const {
    register,
    handleSubmit,
    formState: { dirtyFields, errors, isSubmitting },
    getValues,
  } = useForm<FormValueType>({ mode: 'onChange' })

  const submitSignUp = (data: FormValueType) => {
    signUp(data).then(res => {
      const { status, data }: { status: number; data: { message: string } } = res!
      if (status === 200) {
        routeTo('/user/login')
      } else if (status === 400) {
        alert(data.message)
      } else {
        alert('회원가입에 실패했습니다. 잠시 후 다시 시도해주세요')
      }
    })
  }

  const props = {
    register,
    onSubmit: handleSubmit(data => submitSignUp(data)),
    dirtyFields,
    errors,
    getValues,
    isSubmitting,
  }

  return <SingUpView {...props} />
}

export default SignUp
