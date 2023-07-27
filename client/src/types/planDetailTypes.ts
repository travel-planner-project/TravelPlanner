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

export interface ScheduleProps {
  scheduleData: {
    dateId: number
    dateTitle: string
    scheduleItemList: {
      dateId: number
      itemId: number
      itemTitle: string
      itemTime: string
      category: string
      itemContent: string
      isPrivate: boolean
      budget: number | null
      itemAddress: string
    }[]
  }[]
}

export interface PlanDetailProps {
  userId: number
  chatModal: boolean
  onChatModalTrue: () => void
}

export interface PlanDetailViewProps extends ChattingProps, PlanDetailProps, ScheduleProps {}

export type ChatModalProps = {
  userId: number
  chatList: Chat[]
  newChat: string
  onChatModalFalse: () => void
  onChatChange: (event: any) => void
  onChatSubmit: (event: any) => void
}
