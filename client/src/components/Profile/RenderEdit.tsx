import { useForm } from 'react-hook-form'
import styles from './RenderEdit.module.scss'
import Password from '../SignUp/Password'
import PasswordCheck from '../SignUp/PasswordCheck'
import { FormValueType } from '../../types/signUpTypes'

function RenderEdit() {
  const {
    register,
    handleSubmit,
    formState: { dirtyFields, errors, isSubmitting },
    getValues,
  } = useForm<FormValueType>({ mode: 'onChange' })

  return (
    <form className={styles.entireContainer}>
      <Password register={register} dirtyFields={dirtyFields} errors={errors} />
      <PasswordCheck
        register={register}
        dirtyFields={dirtyFields}
        errors={errors}
        getValues={getValues}
      />
      <button type='submit' className={styles.confirmButton}>
        확인
      </button>
    </form>
  )
}

export default RenderEdit
