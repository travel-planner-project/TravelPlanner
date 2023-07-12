export type Chat = {
  userId: number
  userNickname: string
  // userProfile: string
  message: string
}

export interface ChattingProps {
  chatList: Chat[]
  newChat: string
  onChatModalFalse: () => void
  onChatChange: (event: any) => void
  onChatSubmit: (event: any) => void
}

export interface PlanDetailProps {
  userId: number
  chatModal: boolean
  onChatModalTrue: () => void
}

export interface PlanDetailViewProps extends ChattingProps, PlanDetailProps {}

export type ChatModalProps = {
  userId: number
  chatList: Chat[]
  newChat: string
  onChatModalFalse: () => void
  onChatChange: (event: any) => void
  onChatSubmit: (event: any) => void
}
