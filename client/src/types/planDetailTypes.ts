export type Chat = {
  id: number
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

export type ScheduleType = {
  // dateId: number
  itemId: number
  itemTitle: string
  itemTime: string
  category: string
  budget: number
  itemContent: string
  isPrivate: boolean
  itemAddress: string
}

export type DateType = {
  dateId: number
  dateTitle: string
  dateContent: string
  scheduleItemList: ScheduleType[]
}

export type DateListType = DateType[]

export interface ScheduleProps {
  dateListData: DateListType
  handleEditDate: (id: number, date: string) => void
  handleAddDateBtnClick: () => void
  isEditingDate: boolean
  isEditingDateList: boolean
  editingDateId: number
  handleEditDateBtnClick: (id: number) => void
  handleEditDateListBtnClick: () => void
  handleCancelEditingDate: () => void
  handleCancelEditingDateList: () => void
  handleDeleteDate: (id: number) => void
  handleOpenScheduleEditor: (id: number) => void
  handleCloseScheduleEditor: () => void
  currentDateId: number
  isScheduleEditorOpened: boolean
  onScheduleInputChange: (field: string, value: string) => void
  onScheduleSubmit: (e: React.FormEvent, dateId: number) => void
  onScheduleCategoryChange: (selectedOption: string) => void
  scheduleData: ScheduleType
  handleDeleteSchedule: (dateId: number, itemId: number) => void
  handleEditScheduleBtnClick: (dateId: number, itemId: number) => void
  handleEditSchedule: (e: React.FormEvent, dateId: number, itemId: number) => void
  editingScheduleId: number
  handleCancelEditingScheduleBtnClick: () => void
}

export type GroupMemberListType = {
  groupMemberId: number
  email: string
  nickname: string
  profileImageUrl: string
  role: string
}[]

export interface PlanDetailProps {
  userId: number
  chatModal: boolean
  groupMember: GroupMemberListType
  isMember: boolean
  memberDeleteMode: boolean
  onChatModalTrue: () => void
  onInviteModalOpen: () => void
  handleMemberDeleteMode: () => void
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
