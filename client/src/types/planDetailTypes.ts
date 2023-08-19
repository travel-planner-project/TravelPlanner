export type Chat = {
  userId: number
  userNickname: string
  profileImgUrl: string
  message: string
}

export interface ChattingProps {
  chatList: Chat[]
  newChat: string
  onChatModalFalse: () => void
  onChatChange: (event: any) => void
  onChatSubmit: (event: any) => void
}

type dataType = {
  dateId: number
  itemTitle: string
  itemDate: string
  category: string | null
  budget: number
  itemContent: string
  isPrivate: boolean
  itemAddress: string
}

export interface ScheduleProps {
  planDetailData: {
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
  handleAddDateBtnClick: () => void
  handleOpenScheduleEditor: (id: number) => void
  handleCloseScheduleEditor: () => void
  currentDateId: number
  isScheduleEditorOpened: boolean
  onScheduleInputChange: (field: string, value: string) => void
  onScheduleSubmit: (e: React.FormEvent, dateId: number) => void
  onScheduleCategoryChange: (selectedOption: string) => void
  scheduleData: dataType
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
