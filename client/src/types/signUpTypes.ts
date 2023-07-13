import {
  FieldErrors,
  UseFormRegister,
  UseFormGetValues,
  UseFormHandleSubmit,
} from 'react-hook-form'

export type FormValueType = {
  email: string
  nickname: string
  password: string
  passwordCheck: string
}

type DirtyFields = {
  email: boolean | undefined
  nickname: boolean | undefined
  password: boolean | undefined
  passwordCheck: boolean | undefined
}

export interface ReactHookFormProps {
  register: UseFormRegister<FormValueType>
  dirtyFields: Partial<DirtyFields>
  errors: FieldErrors<FormValueType>
}

export interface PasswordCheckProps extends ReactHookFormProps {
  getValues: UseFormGetValues<FormValueType>
}

export interface SignUpViewProps extends PasswordCheckProps {
  handleSubmit: UseFormHandleSubmit<FormValueType, undefined>
  isSubmitting: boolean
}