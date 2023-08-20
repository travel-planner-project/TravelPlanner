import { atom, RecoilState, DefaultValue } from 'recoil'

type UserInfoType = {
  userId: number
  userNickname: string
  profileImgUrl: string
  email: string
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

export const userInfo: RecoilState<UserInfoType> = atom({
  key: 'userInfo',
  default: {},
  effects_UNSTABLE: [sessionStorageEffect('userInfo')],
})

export type ModalSubmitDataType = {
  planTitle: string
  isPrivate: boolean
}

type ModalType = {
  isOpen: boolean
  title: string
  description: string
  placeholder: string
  submitButton: string
  isSearchBtn?: boolean
  onSubmit: (modalSubmitData: ModalSubmitDataType) => void
}

export const modalState = atom<ModalType>({
  key: 'modalState',
  default: {
    isOpen: false,
    title: '',
    description: '',
    placeholder: '',
    submitButton: '',
    onSubmit: () => {},
  },
})
