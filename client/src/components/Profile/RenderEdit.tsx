import styles from './RenderEdit.module.scss'
import Password from '../SignUp/Password'
import PasswordCheck from '../SignUp/PasswordCheck'
import { SignUpViewProps } from '../../types/signUpTypes'

function RenderEdit({
  register,
  onSubmit,
  dirtyFields,
  errors,
  getValues,
  isSubmitting,
}: SignUpViewProps) {
  return (
    <form className={styles.entireContainer} onSubmit={onSubmit}>
      <Password register={register} dirtyFields={dirtyFields} errors={errors} isEdit={'새 '} />
      <PasswordCheck
        register={register}
        dirtyFields={dirtyFields}
        errors={errors}
        getValues={getValues}
        isEdit={'새 '}
      />
      <button type='submit' className={styles.confirmButton} disabled={isSubmitting}>
        확인
      </button>
    </form>
  )
}

export default RenderEdit
