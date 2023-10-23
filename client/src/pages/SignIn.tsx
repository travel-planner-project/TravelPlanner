import { Link } from 'react-router-dom'
import { useSetRecoilState } from 'recoil'
import { userInfo } from '../store/store'
import Password from '../components/SignIn/Password'
import Email from '../components/SignIn/Email'
import { signIn } from '../apis/user'
import useRouter from '../hooks/useRouter'
import styles from './SignIn.module.scss'

type SignInViewProps = {
  submitSignIn: (event: React.FormEvent<HTMLFormElement>) => void
}

function SignInView({ submitSignIn }: SignInViewProps) {
  return (
    <form className={styles.signInForm} onSubmit={submitSignIn}>
      <Email />
      <Password />
      <div className={styles.btnBox}>
        <button type='submit' className={styles.signInBtn}>
          로그인
        </button>
        <Link to='/user/register'>회원가입</Link>
        <Link to='/findpassword'>비밀번호 찾기</Link>
      </div>
    </form>
  )
}

type responseDataType = {
  userId: number
  userNickname: string
  email: string
  profileImgUrl: string
  message?: string
}

function SignIn() {
  const { routeTo } = useRouter()
  const setUserInfo = useSetRecoilState(userInfo)
  const submitSignIn = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault()

    const formData = new FormData(event.currentTarget)
    const email = formData.get('email')
    const password = formData.get('password')
    const response = await signIn({ email, password })
    const { status, data }: { status: number; data: responseDataType } = response!

    if (status === 200) {
      setUserInfo({
        userId: data.userId,
        userNickname: data.userNickname,
        email: data.email,
        profileImgUrl: data.profileImgUrl,
      })
      routeTo('/')
    } else if (status === 400) {
      alert(data.message)
    } else {
      alert('로그인에 실패했습니다. 잠시 후 다시 시도해주세요')
    }
  }

  return <SignInView submitSignIn={submitSignIn} />
}

export default SignIn
