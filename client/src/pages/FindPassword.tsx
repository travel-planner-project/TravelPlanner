import { useState } from 'react'
import { changePassword, findPassword } from '../apis/user'
import Email from '../components/SignIn/Email'
import styles from './FindPassword.module.scss'
import { useNavigate, useSearchParams } from 'react-router-dom'
import { useForm } from 'react-hook-form'
import { FormValueType } from '../types/signUpTypes'
import RenderEdit from '../components/Profile/RenderEdit'

const FindPassword = () => {
  const [searchParams] = useSearchParams()
  const navigate = useNavigate()
  const token = searchParams.get('token')
  const [isSendingEmail, setIsSendingEmail] = useState(false)

  const sendFindPasswordEmail = (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault()

    setIsSendingEmail(true)
    const formData = new FormData(event.currentTarget)
    const email = formData.get('email')
    findPassword(email)
      .then(res => {
        alert('비밀번호 재설정 링크가 전송되었습니다.')
        setIsSendingEmail(false)
      })
      .catch(err => {
        alert('이메일 전송에 실패했습니다. 잠시 후 다시 시도해주세요.')
        setIsSendingEmail(false)
      })
  }

  const {
    register,
    handleSubmit,
    formState: { dirtyFields, errors, isSubmitting },
    getValues,
  } = useForm<FormValueType>({ mode: 'onChange' })

  const submitNewPassword = (data: FormValueType) => {
    if (!token) return
    changePassword(data.password, token).then(response => {
      if (response?.status === 200) {
        alert('비밀번호가 변경되었습니다. 다시 로그인해주세요.')
        navigate('/user/login')
      }
      if (response?.status !== 200) {
        alert('비밀번호 변경에 실패했습니다.')
      }
    })
  }

  return (
    <div>
      {!token ? (
        <form className={styles.container} onSubmit={sendFindPasswordEmail}>
          <Email />
          <div className={styles.text}>
            로그인 이메일을 입력해주세요. <br />
            해당 메일로 비밀번호 재설정 링크가 전송됩니다.
          </div>
          <button
            type='submit'
            className={isSendingEmail ? styles.disabledBtn : styles.submitBtn}
            disabled={isSendingEmail}
          >
            이메일 전송
          </button>
        </form>
      ) : (
        <RenderEdit
          register={register}
          onSubmit={handleSubmit(data => submitNewPassword(data))}
          dirtyFields={dirtyFields}
          errors={errors}
          getValues={getValues}
          isSubmitting={isSubmitting}
        />
      )}
    </div>
  )
}

export default FindPassword
