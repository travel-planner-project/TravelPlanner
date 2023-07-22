import { atom, RecoilState, DefaultValue } from 'recoil'

type UserInfoType = {
  userId?: number
  userNickname?: string
  profileImg?: string | null
  plannerCount?: number
  friends?: number[] // 닉네임은 중복될 수 있으니 userId
}

type AtomEffectParameterType<T> = {
  setSelf: (dat: T | DefaultValue) => void
  onSet: (callBack: (newValue: T, oldValue: T | DefaultValue, isReset: boolean) => void) => void
}

const isBrowser = () => typeof window !== 'undefined'

const sessionStorageEffect =
  (key: string) =>
  ({ setSelf, onSet }: AtomEffectParameterType<any>) => {
    if (isBrowser()) {
      const savedValue = sessionStorage.getItem(key)
      if (savedValue !== null) {
        setSelf(JSON.parse(savedValue))
      }

      onSet((newValue, _, isReset) => {
        if (isReset) {
          sessionStorage.removeItem(key)
        } else {
          sessionStorage.setItem(key, JSON.stringify(newValue))
        }
      })
    }
  }

// eslint-disable-next-line import/prefer-default-export
export const userInfo: RecoilState<UserInfoType> = atom({
  key: 'userInfo',
  default: {},
  effects_UNSTABLE: [sessionStorageEffect('userInfo')],
})
