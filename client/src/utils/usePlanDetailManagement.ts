import { useState } from 'react'

export const initialScheduleData = {
  itemId: -1,
  itemTime: '',
  itemTitle: '',
  category: '',
  itemAddress: '',
  budget: 0,
  itemContent: '',
  isPrivate: false,
}

export function usePlanDetailManagement() {
  const [isScheduleEditorOpened, setIsScheduleEditorOpened] = useState(false)
  const [editingScheduleId, setEditingScheduleId] = useState<number>(-1)
  const [currentDateId, setCurrentDateId] = useState(-1)

  // date 수정 관련
  const [isEditingDateList, setIsEditingDateList] = useState<boolean>(false)
  const [isEditingDate, setIsEditingDate] = useState<boolean>(false)
  const [editingDateId, setEditingDateId] = useState<number>(-1)

  // 단일 스케줄 최신화 관련

  const [scheduleData, setScheduleData] = useState(initialScheduleData)

  const handleEditDateListBtnClick = () => {
    setIsEditingDateList(true)
  }

  const handleEditDateBtnClick = (id: number) => {
    setEditingDateId(id)
    setIsEditingDate(true)
  }

  const handleCancelEditingDateList = () => {
    setIsEditingDateList(false)
    setIsEditingDate(false)
    setEditingDateId(-1)
  }

  const handleCancelEditingDate = () => {
    setIsEditingDate(false)
    setEditingDateId(-1)
  }

  const handleOpenScheduleEditor = (id: number) => {
    if (isEditingDateList || isEditingDate) {
      alert('날짜 수정을 완료한 후에 다시 시도해주세요.')
      return
    }
    setCurrentDateId(id)
    setIsScheduleEditorOpened(true)
  }

  const onScheduleInputChange = (field: string, value: string) => {
    setScheduleData(prevData => ({
      ...prevData,
      [field]: value,
    }))
  }
  const onScheduleCategoryChange = (selectedOption: string) => {
    setScheduleData(prevData => ({
      ...prevData,
      category: selectedOption,
    }))
  }

  const handleCloseScheduleEditor = () => {
    setCurrentDateId(-1)
    setIsScheduleEditorOpened(false)
    setScheduleData(initialScheduleData)
  }

  const handleCancelEditingScheduleBtnClick = () => {
    setEditingScheduleId(-1)
    setScheduleData(initialScheduleData)
  }

  return {
    isScheduleEditorOpened,
    setIsScheduleEditorOpened,
    editingScheduleId,
    setEditingScheduleId,
    currentDateId,
    setCurrentDateId,
    isEditingDateList,
    setIsEditingDateList,
    isEditingDate,
    setIsEditingDate,
    editingDateId,
    setEditingDateId,
    scheduleData,
    setScheduleData,
    handleEditDateListBtnClick,
    handleEditDateBtnClick,
    handleCancelEditingDate,
    handleCancelEditingDateList,
    handleOpenScheduleEditor,
    handleCloseScheduleEditor,
    handleCancelEditingScheduleBtnClick,
    onScheduleCategoryChange,
    onScheduleInputChange,
  }
}
